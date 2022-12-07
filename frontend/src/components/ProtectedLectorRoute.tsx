import React, { ReactNode, useContext } from "react"
import { AuthContext } from "@root/exports"
import { LoginType } from "@root/enums"
import { Navigate } from "react-router-dom"

const ProtectedRoute = ({ children }: { children: ReactNode }) => {
    const { value } = useContext(AuthContext)
  
    if (!value || value.role !== LoginType.LECTOR)
    {
        return <Navigate to="/" replace />
    }
  
    return <>
        {children}
    </>
}

export default ProtectedRoute