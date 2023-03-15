export interface User{
    idUser: number,
    email: string,
    login: string,
    roles: Role[];
}

export interface Role{
    roleName: string
}