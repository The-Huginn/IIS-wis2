import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { RouteHandles } from "@root/enums"
import { AuthContext, ErrorContext, getValidation, SchemaType } from "@root/exports"
import { Course, CourseDate, Lector, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner, Table } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useLocation, useNavigate } from "react-router"
import { Link } from "react-router-dom"

const CourseManagerViewDateLector = () => {
    const errorContext = useContext(ErrorContext)
    const { value } = useContext(AuthContext)

    const [courseDate, setCourseDate] = useState<CourseDate | null>(null)
    const [loading, setLoading] = useState<boolean>(true)

    const { state } = useLocation()
    const navigate = useNavigate()

    useEffect(() => {
        if (!state)
        {
            return
        }

        if (!state.courseDateId || !value) // Not found course date's id
        {
            navigate(-1)
            return
        }

        value.getCourseDateLector(state.courseDateId)
        .then(courseDate => setCourseDate(courseDate))
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }, [])

    const Content = () => {
        if (loading)
        {
            return <Loading/>
        }
    
        if (courseDate === null)
        {
            navigate(-1)
            return null
        }

        return <div className="p-3 p-md-5">
            <Row>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Popis termínu*</Form.Label>
                        <Form.Control disabled value={courseDate.courseDate.description} className="pe-5" size="lg" type="text" placeholder="Cvičení 1"/>
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Datum konání*</Form.Label>
                        <Form.Control disabled value={courseDate.courseDate.date} className="pe-5" size="lg" type="text" placeholder="1.1.2023"/>
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Čas konání*</Form.Label>
                        <Form.Control disabled value={courseDate.courseDate.time} className="pe-5" size="lg" type="text" placeholder="09:00"/>
                    </Form.Group>
                </Col>
                <Col xs={12} className="text-center mt-3">
                    <Form.Group className="mb-2">
                        <Form.Label as="h4">Přihlášení studenti</Form.Label>
                        {courseDate.evaluations.length ? <Table responsive>
                            <thead>
                                <tr>
                                    <th>Uživatelské jméno</th>
                                    <th>Jméno a příjmení</th>
                                    <th>Hodnocení</th>
                                    <th>Akce</th>
                                </tr>
                            </thead>
                            <tbody className="align-middle">
                                {courseDate.evaluations.map(evaluation => <tr key={evaluation.id}>
                                    <td>{evaluation.student.username}</td>
                                    <td>{evaluation.student.name} {evaluation.student.surname}</td>
                                    <td>{evaluation.evaluation}</td>
                                    <td className="d-flex justify-content-center">
                                        <Link to="evaluate" state={{ evaluationId: evaluation.id, studentName: evaluation.student.name + " " + evaluation.student.surname, studentUsername: evaluation.student.username, evaluation: evaluation.evaluation }}>
                                            <Button variant="warning" className="d-flex align-items-center">
                                                <i className="bi bi-123 me-1"></i>
                                                <span>Ohodnotit</span>
                                            </Button>
                                        </Link>
                                    </td>
                                </tr>)}
                            </tbody>
                        </Table> : <p className="text-center fw-bold h5">Nebyli nalezeni žádní studenti přihlášení k termínu.</p>}
                    </Form.Group>
                </Col>
            </Row>
        </div>
    }

    return <ContentLayout goBack title="Zobrazit termín">
        <Content/>
    </ContentLayout>
}

export default CourseManagerViewDateLector