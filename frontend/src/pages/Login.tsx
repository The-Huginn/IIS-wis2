import { useContext, useEffect, useState } from "react"
import { FieldValues, useForm } from "react-hook-form"
import { Card, Form, Button, Spinner } from "react-bootstrap"

import API from "@root/classes/Api"
import { AuthContext, getValidation, SchemaType } from "@root/exports"
import { LoginType, RouteHandles } from "@root/enums"
import { Link, useNavigate } from "react-router-dom"

const schema: SchemaType = {
    "username": {
        required: true,
        // pattern: /x[a-z]{5}[0-9]{2}$/
    },
    "password": {
        required: true
    }
}

const Login = () => {
    const [authError, setAuthError] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)

    const { setValue } = useContext(AuthContext)

    const { register, handleSubmit, formState: { errors }, watch } = useForm({
        defaultValues: {
            "username": "",
            "password": ""
        }
    })

    const watchUsername = watch("username"), watchPassword = watch("password")

    const navigate = useNavigate()

    const onFormSubmit = async (data: FieldValues) => {
        setLoading(true)
        setAuthError(false)

        fetch(API.apiUrl + "/public/userinfo", {
            headers: {
                Authorization: "Basic " + API.setAuth(data.username, data.password)
            }
        }).then(res => {
            if (!res.ok)
            {
                throw Error(res.statusText)
            }

            return res.json()
        }).then(loginData => {
            const role = loginData.roles[0] === "admin" ? LoginType.ADMIN : loginData.roles[0] === "lector" ? LoginType.LECTOR : LoginType.STUDENT

            setValue(new API(data.username, data.password, role))

            navigate(RouteHandles.DASHBOARD)
        }).catch(e => {
            setAuthError(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    // Clear errors on input change
    useEffect(() => {
        if (authError)
        {
            setAuthError(false)
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [watchUsername, watchPassword])

    return <>
        <div className="d-flex justify-content-center align-items-center h-100">
            <Card>
                <Card.Header>
                    <span>Přihlášení do systému</span>
                </Card.Header>
                <Card.Body>
                    <Form onSubmit={handleSubmit(onFormSubmit)}>
                        <Form.Group className="mb-2 has-validation">
                            <Form.Label>Přihlašovací jméno*</Form.Label>
                            <Form.Control className="pe-5" size="lg" isInvalid={!!errors["username"] || authError} {...getValidation(register, schema, "username")} type="text" placeholder="xlogin00"/>
                            {errors["username"] && <Form.Control.Feedback type="invalid">
                                 Prosím vyplňte přihlašovací jméno.
                            </Form.Control.Feedback>}
                        </Form.Group>
                        <Form.Group className={"has-validation"}>
                            <Form.Label>Heslo*</Form.Label>
                            <Form.Control className="pe-5" size="lg" isInvalid={!!errors["password"] || authError} {...getValidation(register, schema, "password")} type="password" placeholder="Heslo"/>
                            <Form.Control.Feedback type="invalid">
                                {authError ? "Zadané údaje jsou nesprávné." : "Prosím vyplňte heslo."}
                            </Form.Control.Feedback>
                        </Form.Group>
                        <div className="d-flex mt-3">
                            <Link to="/">
                                <Button disabled={loading} variant="danger" type="reset" href="/">Zrušit</Button>
                            </Link>
                            <Button disabled={loading} className="ms-auto d-block" type="submit">{loading ? <Spinner size="sm"/> : "Odeslat"}</Button>
                        </div>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    </>
}

export default Login