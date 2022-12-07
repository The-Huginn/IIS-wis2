import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import { RouteHandles } from "@root/enums"
import { AuthContext, getValidation, SchemaType } from "@root/exports"
import { Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useNavigate } from "react-router"

const schema: SchemaType = {
    "code": {
        required: true
    },
    "description": {
        required: true
    }
}

const RoomManagerAdd = () => {
    const { value } = useContext(AuthContext)

    const [error, setError] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)

    const { register, handleSubmit, formState: { errors }, watch } = useForm({
        defaultValues: {
            "code": "",
            "description": ""
        }
    })

    const watchCode = watch("code"), watchDescription = watch("description")

    const navigate = useNavigate()

    const onFormSubmit = (data: Omit<Room, "id">) => {
        setLoading(true)
        setError(false)

        if (!value)
        {
            return
        }

        value.createRoom(data).then(data => {
            navigate(RouteHandles.ROOM_MANAGER)
        }).catch(e => {
            setError(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    // Clear errors on input change
    useEffect(() => {
        if (error)
        {
            setError(false)
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [watchCode, watchDescription])

    return <ContentLayout goBack title="Přidat místnost">
        <Form onSubmit={handleSubmit(onFormSubmit)} className="p-3 p-md-5">
            <Row>
                <Col xs={12}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Kód místnosti*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["code"] || error} {...getValidation(register, schema, "code")} type="text" placeholder="A01"/>
                        {errors["code"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte kód místnosti.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12}>
                    <Form.Group className={"mb-2 has-validation"}>
                        <Form.Label>Popis místnosti*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["description"] || error} {...getValidation(register, schema, "description")} as="textarea" placeholder="Kancelář"/>
                        {errors["description"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte popis místnosti.
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

export default RoomManagerAdd