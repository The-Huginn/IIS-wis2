import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { RouteHandles } from "@root/enums"
import { AuthContext, ErrorContext, getValidation, SchemaType } from "@root/exports"
import { Course, CourseFull, Lector, Room, Student } from "@root/interfaces"
import { SyntheticEvent, useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner, Table } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useLocation, useNavigate } from "react-router"
import { Link } from "react-router-dom"

const schema: SchemaType = {
    "code": {
        required: true
    },
    "description": {
        required: true
    }
}

const CourseManagerViewGuarant = () => {
    const errorContext = useContext(ErrorContext)
    const { value } = useContext(AuthContext)

    const navigate = useNavigate()

    const { state } = useLocation()

    const [loading, setLoading] = useState<boolean>(true)
    const [course, setCourse] = useState<CourseFull | null>(null)
    const [lectors, setLectors] = useState<Array<Lector>>([])
    const [selectedLectors, setSelectedLectors] = useState<Array<number>>([])

    useEffect(() => {
        if (!state)
        {
            return
        }

        if (!state.courseId || !value) // Not found course's id
        {
            navigate(-1)
            return
        }

        value.getCourseLector(state.courseId).then(course => {
            setCourse(course)

            return value.getAllLectorsGuarant().then(lectors => setLectors(lectors))
        })
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }, [state])

    useEffect(() => {
        if (course === null)
        {
            return
        }

        const newSelectedLectors: number[] = []

        course.lectors.forEach(lector => newSelectedLectors.push(lector.id))

        setSelectedLectors(newSelectedLectors)
    }, [course?.lectors])

    const addStudent = (courseId: number, studentId: number) => {
        if (!value)
        {
            return
        }

        value.addStudentToCourse(courseId, studentId)
        .then(() => value.getCourseLector(state.courseId)
        .then(course => setCourse(course)))
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    const addLector = (courseId: number, lectorId: number) => {
        if (!value)
        {
            return
        }

        value.addLectorToCourse(courseId, lectorId)
        .then(() => value.getCourseLector(state.courseId)
        .then(course => setCourse(course)))
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    const removeLector = (courseId: number, lectorId: number) => {
        if (!value)
        {
            return
        }

        value.removeLectorFromCourse(courseId, lectorId)
        .then(() => value.getCourseLector(state.courseId)
        .then(course => setCourse(course)))
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    const Content = () => {
        if (loading)
        {
            return <Loading/>
        }

        if (course === null)
        {
            navigate(-1)
            return null
        }

        return <Form>
            <Row>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2">
                        <Form.Label>K??d kurzu*</Form.Label>
                        <Form.Control disabled value={course.course.code} className="pe-5" size="lg" type="text" placeholder="IMA1"/>
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2">
                        <Form.Label>Jm??no kurzu*</Form.Label>
                        <Form.Control disabled value={course.course.name} className="pe-5" size="lg" type="text" placeholder="Matematick?? anal??za"/>
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2">
                        <Form.Label>Garant*</Form.Label>
                        <Form.Control disabled value={`${course.course.guarant.name} ${course.course.guarant.surname}`} className="pe-5" size="lg" type="text" placeholder="Matematick?? anal??za"/>
                    </Form.Group>
                </Col>
                <Col xs={12}>
                    <Form.Group className="mb-2">
                        <Form.Label>Popis kurzu*</Form.Label>
                        <Form.Control disabled value={course.course.description} className="pe-5" size="lg" as="textarea" placeholder="Kurz se zab??v??..."/>
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Lekto??i</Form.Label>
                        <Row>
                            {lectors.map(lector => 
                            <Col xs={12} md={6} lg={4} xl={3}>
                                <Form.Check 
                                    type="checkbox"
                                    label={`${lector.name} ${lector.surname}`}
                                    defaultChecked={selectedLectors.includes(lector.id)}
                                    onChange={(e) => {
                                        if (e.target.checked)
                                        {
                                            addLector(course.course.id, lector.id)
                                        }
                                        else
                                        {
                                            removeLector(course.course.id, lector.id)
                                        }
                                    }}
                                />
                            </Col>)}
                        </Row>
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Term??ny</Form.Label>
                        {course.dates.length ? <Table responsive>
                            <thead>
                                <tr>
                                    <th>Popis</th>
                                    <th>M??stnost</th>
                                    <th>Datum a ??as</th>
                                    <th>Akce</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {course.dates.map(courseDate => <tr key={courseDate.id}>
                                    <td>{courseDate.description}</td>
                                    <td>{courseDate.room.code}</td>
                                    <td>{courseDate.date} {courseDate.time}</td>
                                    <td className="d-flex justify-content-center">
                                        <Link to="date-edit" state={{ courseDateId: courseDate.id, courseId: course.course.id }}>
                                            <Button variant="warning" className="d-flex align-items-center">
                                                <i className="bi bi-pencil me-1"></i>
                                                <span>Upravit term??n</span>
                                            </Button>
                                        </Link>
                                    </td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyly nalezeny ????dn?? term??ny.</p>}
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Studenti</Form.Label>
                        {course.students.length ? <Table responsive className="text-center">
                            <thead>
                                <tr>
                                    <th>U??ivatelsk?? jm??no</th>
                                    <th>Jm??no a p????jmen??</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {course.students.map(student => <tr key={student.id}>
                                    <td>{student.username}</td>
                                    <td>{student.name} {student.surname}</td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyli nalezeni ????dn?? studenti p??ihl????en?? ke kurzu.</p>}
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Studenti vy??k??vaj??c?? na potvrzen??</Form.Label>
                        {course.studentsWithRegistration.length ? <Table responsive>
                            <thead>
                                <tr>
                                    <th>U??ivatelsk?? jm??no</th>
                                    <th>Jm??no a p????jmen??</th>
                                    <th>Akce</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {course.studentsWithRegistration.map(student => <tr key={student.id}>
                                    <td>{student.username}</td>
                                    <td>{student.name} {student.surname}</td>
                                    <td className="d-flex justify-content-center">
                                        <Button onClick={() => addStudent(course.course.id, student.id)} variant="success" className="d-flex align-items-center">
                                            <i className="bi bi-plus-circle me-1"></i>
                                            <span>P??idat do kurzu</span>
                                        </Button>
                                    </td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyli nalezeni ????dn?? studenti vy??k??vaj??c?? na potvrzen??.</p>}
                    </Form.Group>
                </Col>
            </Row>
        </Form>
    }

    return <ContentLayout goBack title="Zobrazit kurz">
        <div className="p-3 p-md-5 pt-0 pt-md-0">
            <Link to="date-add" state={{ courseId: course?.course.id }}>
                <Button className="mt-3 ms-auto d-block">P??idat term??n</Button>
            </Link>
            <Content/>
        </div>
    </ContentLayout>
}

export default CourseManagerViewGuarant