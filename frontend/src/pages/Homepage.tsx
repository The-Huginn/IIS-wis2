import { useContext, useDebugValue } from "react"
import { Button, Col, Row } from "react-bootstrap"
import { Link, Navigate } from "react-router-dom"

import { AuthContext } from "@root/exports"
import { LoginType } from "@root/enums"

const Homepage = () => {
    const { value } = useContext(AuthContext)

    if (value)
    {
        return <Navigate to="/dashboard" replace />
    }

    return <>
        <div className="text-center d-flex justify-content-center align-items-center h-100">
            <div className="content-wrapper">
                <Row xs={1}>
                    <Col>
                        <Link to="/login">
                            <Button className="mb-3">
                                Přihlásit se
                            </Button>
                        </Link>
                    </Col>
                    <Col className="text-center">
                        <Link to="/course-manager">
                            <Button className="mb-3">
                                Zobrazit kurzy
                            </Button>
                        </Link>
                    </Col>
                </Row>
            </div>
        </div>
    </>
}

export default Homepage