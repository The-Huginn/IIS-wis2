import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { AuthContext, ErrorContext, Message } from "@root/exports"
import { Lector } from "@root/interfaces"
import { ReactNode, useCallback, useContext, useEffect, useState } from "react"
import { Button, Card, Spinner, Table } from "react-bootstrap"
import { Link, useLocation } from "react-router-dom"

const LectorManager = () => {
    const [lectors, setLectors] = useState<Array<Lector>>([])
    const [loaded, setLoaded] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(true)

    const errorContext = useContext(ErrorContext)
    const { value } = useContext(AuthContext)
    const location = useLocation()

    useEffect(() => {
        if (value)
        {
            value.getAllLectors().then(lectors => setLectors(lectors)).catch(() => errorContext.setValue(true)).finally(() => setLoaded(true))
        }
    }, [])

    useEffect(() => {
        if (loading)
        {
            setLoading(false)
        }
    }, [loading, lectors])

    const deleteLector = (lectorId: number) => {
        if (!value)
        {
            return
        }

        setLoading(true)

        value.deleteLector(lectorId).then(() => {
            return value.getAllLectors().then(lectors => setLectors(lectors))
        }).catch(e => {
            errorContext.setValue(true)
            console.error(e)
        })
    }

    const Content = useCallback(() => {
        if (loading || !loaded)
        {
            return <Loading/>
        }
    
        if (!lectors.length)
        {
            return <p className="text-center fw-bold h5 mt-3">Nebyl nalezen žádný lektor.</p>
        }

        return <Table striped responsive className="text-center">
            <thead>
                <tr>
                    <th>Uživatelské jméno</th>
                    <th>Jméno a příjmení</th>
                    <th>Akce</th>
                </tr>
            </thead>
            <tbody className="align-middle">
                {lectors.map(lector => <tr key={lector.id}>
                    <td>{lector.username}</td>
                    <td>{lector.name} {lector.surname}</td>
                    <td className="d-flex justify-content-center">
                        <Button onClick={() => deleteLector(lector.id)} variant="danger" className="d-flex align-items-center">
                            <i className="bi bi-x-lg me-1"></i>
                            <span>Smazat</span>
                        </Button>
                    </td>
                </tr>)}
            </tbody>
        </Table>
    }, [loading, lectors])

    return <ContentLayout title="Správa lektorů">
        <Link to="add">
            <Button className="mt-3 me-3 ms-auto d-block">Přidat lektora</Button>
        </Link>
        <hr className="mb-0"/>
        <Content/>
    </ContentLayout>
}

export default LectorManager