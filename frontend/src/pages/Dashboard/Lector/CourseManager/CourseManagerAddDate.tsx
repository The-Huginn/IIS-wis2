import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import { RouteHandles } from "@root/enums"
import { AuthContext, getValidation, SchemaType } from "@root/exports"
import { Course, Lector, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useLocation, useNavigate } from "react-router"

const schema: SchemaType = {
    "date": {
        required: true
    },
    "time": {
        required: true
    },
    "description": {
        required: true
    },
    "roomId": {
        required: true
    }
}

const CourseManagerAddDate = () => {
    const { value } = useContext(AuthContext)

    const [error, setError] = useState<boolean>(false)
    const [rooms, setRooms] = useState<Array<Room>>([])
    const [loading, setLoading] = useState<boolean>(false)

    const { register, handleSubmit, formState: { errors }, watch } = useForm({
        defaultValues: {
            "date": "",
            "time": "",
            "description": "",
            "roomId": ""
        }
    })

    const { state } = useLocation()
    const navigate = useNavigate()

    const watchCode = watch("date"), watchTime = watch("time"), watchDescription = watch("description"), watchRoomId = watch("roomId")

    const onFormSubmit = (data: {
        roomId: string, date: string, time: string, description: string
    }) => {
        console.log(data)
        setLoading(true)
        setError(false)

        if (!value)
        {
            return
        }

        value.addCurseDateToCourse({
            ...data, roomId: Number(data.roomId), courseId: state.courseId
        }).then(data => {
            navigate(RouteHandles.COURSE_MANAGER_VIEW_GUARANT, {
                state: {
                    courseId: state.courseId
                }
            })
        }).catch(e => {
            setError(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

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

        value.getAllRoomsLector().then(rooms => setRooms(rooms))
    }, [])

    // Clear errors on input change
    useEffect(() => {
        if (error)
        {
            setError(false)
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [watchCode, watchDescription, watchTime, watchRoomId])

    return <ContentLayout goBack title="Přidat termín">
        <Form onSubmit={handleSubmit(onFormSubmit)} className="p-3 p-md-5">
            <Row>
                <Col xs={12} lg={8}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Popis termínu*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["description"] || error} {...getValidation(register, schema, "description")} type="text" placeholder="Cvičení 1"/>
                        {errors["description"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte popis termínu.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Místnost*</Form.Label>
                        <Form.Select size="lg" isInvalid={!!errors["roomId"] || error} {...getValidation(register, schema, "roomId")}>
                            <option value="" selected disabled hidden>Vyberte místnost</option>
                            {rooms.map(room => <option value={room.id} key={room.id}>{room.code}</option>)}
                        </Form.Select>
                        {errors["roomId"] && <Form.Control.Feedback type="invalid">
                            Prosím vyberte místnost.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12} lg={6}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Datum konání*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["date"] || error} {...getValidation(register, schema, "date")} type="text" placeholder="1.1.2023"/>
                        {errors["date"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte datum konání.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12} lg={6}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Čas konání*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["time"] || error} {...getValidation(register, schema, "time")} type="text" placeholder="09:00"/>
                        {errors["time"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte čas konání.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12}>
                    <Button disabled={loading} className="ms-auto d-block" type="submit">{loading ? <Spinner size="sm"/> : "Odeslat"}</Button>
                </Col>
            </Row>
        </Form>
    </ContentLayout>
}

export default CourseManagerAddDate