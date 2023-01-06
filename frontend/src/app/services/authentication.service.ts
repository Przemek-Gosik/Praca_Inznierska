import { Injectable } from "@angular/core";
import { RoleConsts } from "../consts/role-consts";
import { Role, User } from "../models/user";
import { LocalstorageService } from "./localstorage.service";

@Injectable({providedIn:'root'})
export class AuthenticationService{
    private user : User | undefined;
    constructor(private localStorageService: LocalstorageService){
        this.user = this.localStorageService.getItemFromStorage('user') 
    }

    public get userRoles():Role[]{
        return this.user?.roles!
    }
    public logInUser():void{
        this.user = this.localStorageService.getItemFromStorage('user') 
    }
    public logOutUser():void{
        this.user = undefined
    }

    public isUserAdmin():boolean{
        
        if(this.user){
            var adminRole: Role = {
                roleName: RoleConsts.ADMIN
            }
            let isAdmin: boolean = false
            for(var role of this.user.roles){
                if(role.roleName == adminRole.roleName){
                    isAdmin = true
                }
            }
            return isAdmin
        }else{
            return false
        }
    }

}