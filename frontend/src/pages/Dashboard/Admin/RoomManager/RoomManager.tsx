import ContentLayout from "@root/components/ContentLayout"
import Loading from "@root/components/Loading"
import { AuthContext, Message } from "@root/exports"
import { Room } from "@root/interfaces"
import { useContext, useEffect, useState } from "react"
import { Button, Card, Spinner, Table } from "react-bootstrap"
import { Link, useLocation } from "react-router-dom"

const RoomManager = () => {
    const [rooms, setRooms] = useState<Array<Room>>([])
    const [loaded, setLoaded] = useState<boolean>(false)
    const [loading, setLoading] = useState<boolean>(false)
    const [error, setError] = useState<boolean>(false)

    const { value } = useContext(AuthContext)
    const location = useLocation()

    useEffect(() => {
        if (value)
        {
            value.getAllRooms().then(rooms => setRooms(rooms)).finally(() => setLoading(false)).finally(() => setLoaded(true))
        }
    }, [])

    useEffect(() => {
        if (loading)
        {
            setLoading(false)
        }
    }, [loading, rooms])

    const deleteRoom = (roomId: number) => {
        if (!value)
        {
            return
        }

        setLoading(true)

        value.deleteRoom(roomId).then(() => {
            return value.getAllRooms().then(rooms => setRooms(rooms))
        }).catch(e => {
            setError(true)
            console.error(e)
        }).finally(() => {
            setLoading(false)
        })
    }

    const Rooms = () => {
        if (loading || !loaded)
        {
            return <Loading/>
        }

        if (!rooms.length)
        {
            return <p className="text-center fw-bold h5 mt-3">Nebyla nalezen žádná místnost.</p>
        }

        return <Table className="text-center">
            <thead>
                <tr>
                    <th className="w-100">Kód místnosti</th>
                    <th>Akce</th>
                </tr>
            </thead>
            <tbody className="align-middle">
                {rooms.map(room => <tr key={room.id}>
                    <td className="w-100">{room.code}</td>
                    <td className="d-flex">
                        <Button onClick={() => deleteRoom(room.id)} variant="danger" className="d-flex align-items-center">
                            <i className="bi bi-x-lg me-1"></i>
                            <span>Smazat</span>
                        </Button>
                    </td>
                </tr>)}
            </tbody>
        </Table>
    }

    return <ContentLayout title="Správa místností">
        <Link to="add">
            <Button className="mt-3 me-3 ms-auto d-block">Přidat místnost</Button>
        </Link>
        <hr className="mb-0"/>
        <Rooms/>
    </ContentLayout>
}

export default RoomManager