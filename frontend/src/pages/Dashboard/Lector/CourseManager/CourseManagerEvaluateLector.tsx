import API from "@root/classes/Api"
import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { RouteHandles } from "@root/enums"
import { AuthContext, getValidation, SchemaType } from "@root/exports"
import { Course, CourseDate, Lector, Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Col, Form, Row, Spinner, Table } from "react-bootstrap"
import { FieldValues, useForm } from "react-hook-form"
import { useLocation, useNavigate } from "react-router"
import { Link } from "react-router-dom"

const schema: SchemaType = {
    "evaluation": {
        min: {
            value: 0,
            message: "Hodnocení musí být větší než 1!"
        },
        max: {
            value: 100,
            message: "Hodnocení musí být menší než 100!"
        }
    },
}

const CourseManagerEvaluateLector = () => {
    const { value } = useContext(AuthContext)

    const [error, setError] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)

    const { state } = useLocation()
    const navigate = useNavigate()

    const { register, handleSubmit, formState: { errors }, watch, setValue } = useForm({
        defaultValues: {
            "evaluation": "0",
        }
    })

    const watchEvaluation = watch("evaluation")

    useEffect(() => {
        if (!state)
        {
            return
        }

        if (state.evaluation === undefined || !state.evaluationId || !state.studentName || !state.studentUsername || !value) // Not found evaluation's id
        {
            return
        }

        setValue("evaluation", state.evaluation)
    }, [])

    const onFormSubmit = (data: {
        evaluation: string
    }) => {
        if (!value)
        {
            return
        }

        value.evaluateCourseDate({
            evaluation: Number(data.evaluation),
            evaluationId: state.evaluationId
        }).then(() => {
            navigate(-1)
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
    }, [watchEvaluation])

    if (loading)
    {
        return <Loading/>
    }

    console.log(errors)

    return <ContentLayout goBack title="Zobrazit termín">
        <Form onSubmit={handleSubmit(onFormSubmit)} className="p-3 p-md-5">
            <Row>
                <Col xs={12} md={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Uživatelské jméno</Form.Label>
                        <Form.Control disabled defaultValue={state.userName} className="pe-5" size="lg" type="text" placeholder="xlogin00"/>
                    </Form.Group>
                </Col>
                <Col xs={12} md={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Jméno a příjmení</Form.Label>
                        <Form.Control disabled defaultValue={state.studentName} className="pe-5" size="lg" type="text" placeholder="Jan Novák"/>
                    </Form.Group>
                </Col>
                <Col xs={12} md={4}>
                    <Form.Group className="mb-2 has-validation">
                        <Form.Label>Hodnocení</Form.Label>
                        <Form.Control className="pe-5" size="lg" isInvalid={!!errors["evaluation"] || error} {...getValidation(register, schema, "evaluation")} type="number" placeholder="0"/>
                        <Form.Control.Feedback type="invalid">
                            {errors["evaluation"]?.message}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
                <Col xs={12}>
                    <Button disabled={loading} className="ms-auto d-block" type="submit">{loading ? <Spinner size="sm"/> : "Uložit"}</Button>
                </Col>
            </Row>
        </Form>
    </ContentLayout>
}

export default CourseManagerEvaluateLector