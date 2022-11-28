import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import { RouteHandles } from "@root/enums"
import { AuthContext, getValidation, SchemaType } from "@root/exports"
import { Course, Lector, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useNavigate } from "react-router"

const schema: SchemaType = {
    "code": {
        required: true
    },
    "name": {
        required: true
    },
    "description": {
        required: true
    },
    "guarant": {
        required: true
    }
}

const CourseManagerAdd = () => {
    const { value } = useContext(AuthContext)

    const [error, setError] = useState<boolean>(false)
    const [lectors, setLectors] = useState<Array<Lector>>([])
    const [loading, setLoading] = useState<boolean>(false)

    const { register, handleSubmit, formState: { errors }, watch } = useForm({
        defaultValues: {
            "code": "",
            "name": "",
            "description": "",
            "guarant": ""
        }
    })

    const watchCode = watch("code"), watchName = watch("name"), watchDescription = watch("description"), watchGuarant = watch("guarant")

    const navigate = useNavigate()

    const onFormSubmit = (data: {
        guarant: string, code: string, name: string, description: string
    }) => {
        setLoading(true)
        setError(false)

        if (!value)
        {
            return
        }

        value.createCourse(data).then(data => {
            navigate(RouteHandles.COURSE_MANAGER)
        }).catch(e => {
            setError(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    useEffect(() => {
        if (!value)
        {
            return
        }

        value.getAllLectors().then(lectors => setLectors(lectors))
    }, [])

    // Clear errors on input change
    useEffect(() => {
        if (error)
        {
            setError(false)
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [watchCode, watchDescription, watchName, watchGuarant])

    return <ContentLayout goBack title="Přidat kurz">
        <Form onSubmit={handleSubmit(onFormSubmit)} className="p-3 p-md-5">
            <Row>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Kód kurzu*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["code"] || error} {...getValidation(register, schema, "code")} type="text" placeholder="IMA1"/>
                        {errors["code"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte kód kurzu.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Jméno kurzu*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["name"] || error} {...getValidation(register, schema, "name")} type="text" placeholder="Matematická analýza"/>
                        {errors["name"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte jméno kurzu.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12} lg={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Garant*</Form.Label>
                        <Form.Select size="lg" isInvalid={!!errors["guarant"] || error} {...getValidation(register, schema, "guarant")}>
                            <option value="" selected disabled hidden>Vyberte garanta</option>
                            {lectors.map(lector => <option value={lector.id} key={lector.id}>{lector.name} {lector.surname}</option>)}
                        </Form.Select>
                        {errors["guarant"] && <Form.Control.Feedback type="invalid">
                            Prosím vyberte garanta.
                        </Form.Control.Feedback>}
                    </Form.Group>
                </Col>
                <Col xs={12}>
                    <Form.Group className={"mb-2 has-validation"}>
                        <Form.Label>Popis kurzu*</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["description"] || error} {...getValidation(register, schema, "description")} as="textarea" placeholder="Kurz se zabývá..."/>
                        {errors["description"] && <Form.Control.Feedback type="invalid">
                            Prosím vyplňte popis kurzu.
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

export default CourseManagerAdd