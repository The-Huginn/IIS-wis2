import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { RouteHandles } from "@root/enums"
import { AuthContext, ErrorContext, getValidation, SchemaType } from "@root/exports"
import { Course, CourseDate, CourseFull, Lector, MyCourseDate, Room, Student } from "@root/interfaces"
import { SyntheticEvent, useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner, Table } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useLocation, useNavigate } from "react-router"

const schema: SchemaType = {
    "code": {
        required: true
    },
    "description": {
        required: true
    }
}

enum RegistrationState
{
    NOT_REGISTERED,
    REGISTERED,
    ACCEPTED
}

const CourseManagerViewStudent = () => {
    const errorContext = useContext(ErrorContext)
    const { value } = useContext(AuthContext)

    const navigate = useNavigate()

    const { state } = useLocation()

    const [isRegistered, setIsRegistered] = useState<RegistrationState>(RegistrationState.NOT_REGISTERED)
    const [loading, setLoading] = useState<boolean>(true)
    const [registeredCourseDates, setRegisteredCourseDates] = useState<Array<MyCourseDate>>([])
    const [course, setCourse] = useState<CourseFull | null>(null)

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

        value.getCourseStudent(state.courseId)
        .then(course => {
            setCourse(course)

            if (course.studentsWithRegistration.some(student => student.username === value.getUsername()))
            {
                setIsRegistered(RegistrationState.REGISTERED)
            }
            else if (course.students.some(student => student.username === value.getUsername()))
            {
                setIsRegistered(RegistrationState.ACCEPTED)
            }

            return value.getAllRegisteredCourseDatesStudent(state.courseId).then(courseDates => setRegisteredCourseDates(courseDates))
        })
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }, [state])

    const signupCourse = (courseId: number) => {
        if (!value)
        {
            return
        }

        setLoading(true)

        value.signUpCourseStudent(courseId)
        .then(() => setIsRegistered(RegistrationState.REGISTERED))
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    const signCourseDate = (courseDateId: number) => {
        if (!value)
        {
            return
        }

        value.signUpCourseDateStudent(courseDateId)
        .then(() => {
            return value.getAllRegisteredCourseDatesStudent(state.courseId)
            .then(courseDates => setRegisteredCourseDates(courseDates))
        })
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    const RegistrationButton = ({ courseId }: { courseId: number }) => {
        if (isRegistered === RegistrationState.NOT_REGISTERED)
        {
            return <Button variant="primary" onClick={() => signupCourse(courseId)}>
                Registrovat se do kurzu
            </Button>
        }
        else if (isRegistered === RegistrationState.REGISTERED)
        {
            return <Button disabled variant="primary">
                Již jste zažádal o registraci
            </Button>
        }

        return <Button disabled variant="primary">
            Již jste přihlášen v kurzu
        </Button>
    }

    const EvaluationValue = ({ courseDateId }: { courseDateId: number }) => {
        const item = registeredCourseDates.find(courseDate => courseDate.courseDate.id === courseDateId)

        let result = "\u00A0" // nbsp

        if (item)
        {
            result = item.eval.toString()
        }

        return <span>{result}</span>
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
                <Col xs={12}>
                    <Form.Group className="mb-2 text-end">
                        <RegistrationButton courseId={course.course.id}/>
                    </Form.Group>
                </Col>
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
                        <Form.Label as="h4">Termíny</Form.Label>
                        {course.dates.length ? <Table responsive>
                            <thead>
                                <tr>
                                    <th>Popis</th>
                                    <th>Místnost</th>
                                    <th>Datum a čas</th>
                                    <th>Hodnocení</th>
                                    <th>Akce</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {course.dates.map(courseDate => <tr key={courseDate.id}>
                                    <td>{courseDate.description}</td>
                                    <td>{courseDate.room.code}</td>
                                    <td>{courseDate.date} {courseDate.time}</td>
                                    <td>
                                        <EvaluationValue courseDateId={courseDate.id}/>
                                    </td>
                                    <td className="d-flex justify-content-center">
                                        {isRegistered === RegistrationState.ACCEPTED && !registeredCourseDates.some(courseDateSome => courseDateSome.courseDate.id === courseDate.id) ? <Button onClick={() => signCourseDate(courseDate.id)} variant="success" className="d-flex align-items-center">
                                            <i className="bi bi-plus-circle me-1"></i>
                                            <span>Přidat do kurzu</span>
                                        </Button> : <span>&nbsp;</span>}
                                    </td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyl nalezen žádné termíny.</p>}
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
            </Row>
        </Form>
    }

    return <ContentLayout goBack title="Zobrazit kurz">
        <Content/>
    </ContentLayout>
}

export default CourseManagerViewStudent