import { ReactNode, useContext } from "react"
import { AuthContext } from "@root/exports"
import { Navigate } from "react-router-dom"
import { LoginType } from "@root/enums"

const ProtectedRoute = ({ children }: { children: ReactNode }) => {
    const { value } = useContext(AuthContext)
  
    if (!value)
    {
        return <Navigate to="/" replace />
    }
  
    return <>
        {children}
    </>
}

export default ProtectedRoute