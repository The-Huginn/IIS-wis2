import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import { RouteHandles } from "@root/enums"
import { AuthContext, getValidation, SchemaType } from "@root/exports"
import { Lector, Student } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useNavigate } from "react-router"

const schema: SchemaType = {
    "username": {
        required: {
            value: true,
            message: "Prosím vyplňte přihlašovací jméno."
        },
        minLength: {
            value: 5,
            message: "Uživatelské jméno musí být delší než 5 znaků!"
        },
        maxLength: {
            value: 20,
            message: "Uživatelské jméno musí být kratší než 20 znaků!"
        },
    },
    "password": {
        required: {
            value: true,
            message: "Prosím vyplňte heslo."
        },
    },
    "name": {
        required: {
            value: true,
            message: "Prosím vyplňte jméno."
        },
    },
    "surname": {
        required: {
            value: true,
            message: "Prosím vyplňte příjmení."
        },
    },
}

const StudentManagerAdd = () => {
    const { value } = useContext(AuthContext)

    const [error, setError] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)

    const { register, handleSubmit, formState: { errors }, watch } = useForm({
        defaultValues: {
            "username": "",
            "password": "",
            "name": "",
            "surname": ""
        }
    })

    const watchUsername = watch("username"), watchPassword = watch("password"), watchSurname = watch("surname"), watchName = watch("name")

    const navigate = useNavigate()

    const onFormSubmit = (data: Omit<Student, "id"> & { password: string }) => {
        setLoading(true)
        setError(false)

        if (!value)
        {
            return
        }

        value.createStudent(data).then(data => {
            navigate(RouteHandles.STUDENT_MANAGER)
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
    }, [watchUsername, watchSurname, watchName, watchPassword])

    return <ContentLayout goBack title="Přidat studenta">
        <Form onSubmit={handleSubmit(onFormSubmit)} className="p-3 p-md-5">
            <Row>
                <Col xs={12} md={6}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Uživatelské jméno*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["username"] || error} {...getValidation(register, schema, "username")} type="text" placeholder="xlogin00"/>
                        <Form.Control.Feedback type="invalid">
                            {errors["username"]?.message}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
                <Col xs={12} md={6}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Heslo*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["password"] || error} {...getValidation(register, schema, "password")} type="password" placeholder="Heslo"/>
                        <Form.Control.Feedback type="invalid">
                            {errors["password"]?.message}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
                <Col xs={12} md={6}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Jméno*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["name"] || error} {...getValidation(register, schema, "name")} type="text" placeholder="Jan"/>
                        <Form.Control.Feedback type="invalid">
                            {errors["name"]?.message}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
                <Col xs={12} md={6}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Příjmení*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["surname"] || error} {...getValidation(register, schema, "surname")} type="text" placeholder="Novák"/>
                        <Form.Control.Feedback type="invalid">
                            {error ? "Uživatel se zadaným uživatelským jménem již existuje." : errors["surname"]?.message}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
                <Col xs={12}>
                    <Button disabled={loading} className="ms-auto d-block" type="submit">{loading ? <Spinner size="sm"/> : "Odeslat"}</Button>
                </Col>
            </Row>
        </Form>
    </ContentLayout>
}

export default StudentManagerAdd