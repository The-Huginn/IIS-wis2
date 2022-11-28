import { AuthContext } from "@root/exports"
import { useContext, useEffect, useState } from "react"
import { Button, Modal } from "react-bootstrap"
import { useLocation } from "react-router"
import { Link } from "react-router-dom"

const DEFAULT_EXPIRY_TIME = 3600000 // Time in miliseconds after which the user will be logged out with no activity
const DEFAULT_EXPIRY_TIME_CHECK = 60000 // Delay between the expiry checks

const ExpiryTimer = () => {
    const [loginExpiryTimer, setLoginExpiryTimer] = useState<number>(0)
    const [isExpiryModalShown, showExpiryModal] = useState<boolean>(false)
    
    const loginExpiryTimerDate = new Date(loginExpiryTimer)

    const expiryString = loginExpiryTimerDate.getHours() * 60 + loginExpiryTimerDate.getMinutes() + "min."

    const auth = useContext(AuthContext)
    const location = useLocation()

    useEffect(() => {
        if (!auth.value)
        {
            return
        }

        // Init the expiry timer. Used for calculating the login expiry time
        const timer = setInterval(() => {
            setLoginExpiryTimer((current) => {
                const newValue = current - DEFAULT_EXPIRY_TIME_CHECK

                if (newValue < 0)
                {
                    // Clear interval. Show expiry modal. Do not logout as that will prevent modal rendery
                    clearInterval(timer)
                    showExpiryModal(true)
                    return 0
                }

                return newValue
            })
        }, DEFAULT_EXPIRY_TIME_CHECK)

        // Clear interval on unmount
        return (() => {
            clearInterval(timer)
        })
    }, [auth])

    useEffect(() => {
        // Route has changed. Update the expiry timer.
        setLoginExpiryTimer(DEFAULT_EXPIRY_TIME)
    }, [location])

    return <>
        <Modal show={isExpiryModalShown}>
            <Modal.Header>
                <Modal.Title>Oznámení</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <span>Z důvodu neaktivity jste byl odhlášen.</span>
            </Modal.Body>
            <Modal.Footer>
                <Link to="/">
                    <Button onClick={() => {
                        auth.setValue(null)
                        showExpiryModal(false)
                    }} variant="primary">Zpět na domovskou obrazovku</Button>
                </Link>
            </Modal.Footer>
        </Modal>
        <span>{expiryString}</span>
    </>
}

export default ExpiryTimer