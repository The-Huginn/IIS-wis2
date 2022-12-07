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

const CourseManagerViewLector = () => {
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

        value.getCourseLector(state.courseId).then(course => setCourse(course))
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

        return <Form className="p-3 p-md-5">
            <Row>
            <Col xs={12} lg={4}>
                    <Form.Group className="mb-2">
                        <Form.Label>Kód kurzu*</Form.Label>
                        <Form.Control disabled value={course.course.code} className="pe-5" size="lg" type="text" placeholder="IMA1"/>
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2">
                        <Form.Label>Jméno kurzu*</Form.Label>
                        <Form.Control disabled value={course.course.name} className="pe-5" size="lg" type="text" placeholder="Matematická analýza"/>
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2">
                        <Form.Label>Garant*</Form.Label>
                        <Form.Control disabled value={`${course.course.guarant.name} ${course.course.guarant.surname}`} className="pe-5" size="lg" type="text" placeholder="Matematická analýza"/>
                    </Form.Group>
                </Col>
                <Col xs={12}>
                    <Form.Group className="mb-2">
                        <Form.Label>Popis kurzu*</Form.Label>
                        <Form.Control disabled value={course.course.description} className="pe-5" size="lg" as="textarea" placeholder="Kurz se zabývá..."/>
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Lektoři</Form.Label>
                        {course.lectors.length ? <Table responsive>
                            <thead>
                                <tr>
                                    <th>Uživatelské jméno</th>
                                    <th>Jméno a příjmení</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {course.lectors.map(lector => <tr key={lector.id}>
                                    <td>{lector.username}</td>
                                    <td>{lector.name} {lector.surname}</td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyli nalezeni žádní lektoři přihlášení ke kurzu.</p>}
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Termíny</Form.Label>
                        {course.dates.length ? <Table responsive>
                            <thead>
                                <tr>
                                    <th>Popis</th>
                                    <th>Místnost</th>
                                    <th>Datum a čas</th>
                                    <th>Akce</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {course.dates.map(courseDate => <tr key={courseDate.id}>
                                    <td>{courseDate.description}</td>
                                    <td>{courseDate.room.code}</td>
                                    <td>{courseDate.date} {courseDate.time}</td>
                                    <td className="d-flex justify-content-center">
                                        <Link to="date-view" state={{ courseDateId: courseDate.id }}>
                                            <Button variant="warning" className="d-flex align-items-center">
                                                <i className="bi bi-123 me-1"></i>
                                                <span>Zobrazit termín</span>
                                            </Button>
                                        </Link>
                                    </td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyly nalezeny žádné termíny.</p>}
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Studenti</Form.Label>
                        {course.students.length ? <Table responsive>
                            <thead>
                                <tr>
                                    <th>Uživatelské jméno</th>
                                    <th>Jméno a příjmení</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {course.students.map(student => <tr key={student.id}>
                                    <td>{student.username}</td>
                                    <td>{student.name} {student.surname}</td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyli nalezeni žádní studenti přihlášení ke kurzu.</p>}
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Studenti vyčkávající na potvrzení</Form.Label>
                        {course.studentsWithRegistration.length ? <Table responsive>
                            <thead>
                                <tr>
                                    <th>Uživatelské jméno</th>
                                    <th>Jméno a příjmení</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {course.studentsWithRegistration.map(student => <tr key={student.id}>
                                    <td>{student.username}</td>
                                    <td>{student.name} {student.surname}</td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyli nalezeni žádní studenti vyčkávající na potvrzení.</p>}
                    </Form.Group>
                </Col>
            </Row>
        </Form>
    }

    return <ContentLayout goBack title="Detail kurzu">
        <Content/>
    </ContentLayout>
}

export default CourseManagerViewLector