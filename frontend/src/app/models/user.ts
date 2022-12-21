export interface User{
    id_user?: number,
    email: string,
    is_email_confirmed: boolean,
    login: string,
    password: string,
    confirmPassword: string;
}