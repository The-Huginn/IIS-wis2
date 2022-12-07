import React from "react"
import Api from "@root/classes/Api"
import { RegisterOptions, UseFormRegister } from "react-hook-form"

export type SchemaType = { [key: string]: RegisterOptions }
export type AuthContextValueType = Api | null

const ErrorContext = React.createContext<{
    value: boolean,
    setValue: (value: boolean) => void
}>({
    value: false, setValue: () => {}
})

const AuthContext = React.createContext<{
    value: AuthContextValueType,
    setValue: (value: AuthContextValueType) => void
}>({
    value: null, setValue: () => {}
})

const getValidation = (register: UseFormRegister<any>, schema: SchemaType, key: string) => {
    return { ...register(key, schema[key]) }
}

enum Message {
    LOADING = "Načítání dat..."
}

export { AuthContext, ErrorContext, getValidation, Message } 