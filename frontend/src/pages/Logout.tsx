import { useContext, useEffect, useState } from "react"

import { useNavigate } from "react-router"
import { Card, Button } from "react-bootstrap"

import { AuthContext } from "@root/exports"
import { Link } from "react-router-dom"
import API from "@root/classes/Api"
import { LoginType } from "@root/enums"

const Logout = () => {
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(true)

    const { value, setValue } = useContext(AuthContext)
    const navigate = useNavigate()

    useEffect(() => {
        // If is logged, show a message, if not, redirect to homepage
        if (value)
        {
            setValue(null)
            setIsLoggedIn(false)
        }
        else
        {
            navigate("/")
        }
    }, [setValue, navigate])

    if (!isLoggedIn)
    {
        return <>
            <div className="d-flex justify-content-center align-items-center h-100">
                <Card>
                    <Card.Header>
                        <span>Oznámení</span>
                    </Card.Header>
                    <Card.Body className="text-center">
                        <p>Byl jste úspěšně odhlášen!</p>
                        <Link to="/">
                            <Button>Zpět na domovskou obrazovku</Button>
                        </Link>
                    </Card.Body>
                </Card>
            </div>
        </>
    }

    return null
}

export default Logout