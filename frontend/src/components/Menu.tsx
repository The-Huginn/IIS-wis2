import { LoginType, RouteHandles } from "@root/enums"
import { AuthContext } from "@root/exports"
import { useContext } from "react"
import { Link } from "react-router-dom"

const paths: {
    [key in LoginType]: Array<{
        path: string, text: string
    }>
} = {
    [LoginType.ADMIN]: [
        {
            path: RouteHandles.STUDENT_MANAGER,
            text: "Správa studentů"
        },
        {
            path: RouteHandles.LECTOR_MANAGER,
            text: "Správa lektorů"
        },
        {
            path: RouteHandles.ROOM_MANAGER,
            text: "Správa místností"
        },
        {
            path: RouteHandles.COURSE_MANAGER,
            text: "Správa kurzů"
        }
    ],
    [LoginType.LECTOR]: [
        {
            path: RouteHandles.COURSE_MANAGER_LECTOR,
            text: "Správa kurzů"
        }
    ],
    [LoginType.STUDENT]: [
        {
            path: RouteHandles.COURSE_MANAGER_STUDENT,
            text: "Správa kurzů"
        }
    ],
    [LoginType.PUBLIC]: []
}

const Menu = () => {
    const auth = useContext(AuthContext)

    if (auth.value === null)
    {
        return null
    }

    const routes = paths[auth.value.role]

    return <>
        {routes.map(route => <Link key={route.text} to={route.path} className="menu-link">
            <div className="p-3">
                {route.text}
            </div>
        </Link>)}
    </>
}

export default Menu