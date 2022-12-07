import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { LoginType, RouteHandles } from "@root/enums"
import { AuthContext, ErrorContext, getValidation, SchemaType } from "@root/exports"
import { Course, CourseDate, Lector, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner, Table } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useLocation, useNavigate } from "react-router"
import { Link } from "react-router-dom"

const schema: SchemaType = {
    "name": {
        required: true
    },
    "surname": {
        required: true
    },
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
    }
}

const Dashboard = () => {
    const errorContext = useContext(ErrorContext)
    const { value } = useContext(AuthContext)

    const [error, setError] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(true)

    const { register, handleSubmit, formState: { errors }, watch, setValue } = useForm({
        defaultValues: {
            "username": "",
            "name": "",
            "surname": "",
        }
    })

    const navigate = useNavigate()

    const watchUsername = watch("username"), watchName = watch("name"), watchSurname = watch("surname")

    const onFormSubmit = (data: {
        username: string, name: string, surname: string
    }) => {
        setLoading(true)
        setError(false)

        if (!value)
        {
            return
        }

        value.changeUserinfo(data)?.catch(e => {
            setError(true)
            console.error(e)
        }).finally(() => {
            navigate("/logout")
            setLoading(false)
        })
    }

    useEffect(() => {
        if (!value)
        {
            return
        }

        value.getNames()
        .then(res => {
            setValue("username", value.getUsername())
            setValue("name", res.name)
            setValue("surname", res.surname)
        })
        .catch(e => {
            errorContext.setValue(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }, [])

    // Clear errors on input change
    useEffect(() => {
        if (error)
        {
            setError(false)
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [watchUsername, watchSurname, watchName])

    if (loading)
    {
        return <Loading/>
    }

    return <ContentLayout goBack title="Profil uživatele">
        <Form onSubmit={handleSubmit(onFormSubmit)} className="p-3 p-md-5">
            <Row>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Uživatelské jméno*</Form.Label>
                        <Form.Control disabled={value?.role === LoginType.ADMIN} className="pe-5" size="lg" isInvalid={!!errors["username"] || error} {...getValidation(register, schema, "username")} type="text" placeholder="xlogin00"/>
                        <Form.Control.Feedback type="invalid">
                            {errors["username"]?.message}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Jméno*</Form.Label>
                        <Form.Control disabled={value?.role === LoginType.ADMIN} className="pe-5" size="lg" isInvalid={!!errors["name"] || error} {...getValidation(register, schema, "name")} type="text" placeholder="Jan"/>
                        {errors["name"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte jméno.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Příjmení*</Form.Label>
                        <Form.Control disabled={value?.role === LoginType.ADMIN} className="pe-5" size="lg" isInvalid={!!errors["surname"] || error} {...getValidation(register, schema, "surname")} type="text" placeholder="Novák"/>
                        {errors["surname"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte příjmení.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12}>
                    <Button disabled={loading} className="ms-auto d-block" type="submit">{loading ? <Spinner size="sm"/> : "Upravit"}</Button>
                </Col>
            </Row>
        </Form>
    </ContentLayout>
}

export default Dashboard