import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { Role } from "../models/user";
import { AuthenticationService } from "../services/authentication.service";


@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate{

    constructor(
        private authenticationService: AuthenticationService,
        private router: Router
    ){}


    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        let roles : Role[] = this.authenticationService.userRoles;
        if(roles){
            var isAuthenticated: boolean = false
            for(var role of roles){
                if(role.roleName == route.data['roles']){
                    isAuthenticated = true
                }
            }
            if(!isAuthenticated){
                this.router.navigate(['/'])
            }
            return isAuthenticated
        }else{
            this.router.navigate(['account/signin'])
            return false;
        }
    }
    
}