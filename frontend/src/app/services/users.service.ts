import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { User } from "../models/user";
import { LocalstorageService } from "./localstorage.service";
import { TokenService } from "./token.service";

@Injectable({
    providedIn: 'root'
  })
  export class UsersService {
  
    private apiUrl="http://localhost:8080/api/admin/user/";

    constructor(
        private http: HttpClient,    
        private localstorage: LocalstorageService,
        private tokenService : TokenService) { }

    getAllUsers():Observable<User[]>{
        return this.http.get<User[]>(`${this.apiUrl}`,{headers:this.tokenService.getHeaderWithToken()})
    }

    getUser(id:number):Observable<User>{
        return this.http.get<User>(`${this.apiUrl}${id}`,{headers:this.tokenService.getHeaderWithToken()})
    }

    deleteUser(id:number){
        return this.http.delete(`${this.apiUrl}${id}`,{headers:this.tokenService.getHeaderWithToken()})
    }

    giveAdminRole(id:number):Observable<User>{
        return this.http.patch<User>(`${this.apiUrl}give-role/${id}`,{},{headers:this.tokenService.getHeaderWithToken()})
    }

    takeAdminRole(id:number):Observable<User>{
        return this.http.patch<User>(`${this.apiUrl}take-role/${id}`,{},{headers:this.tokenService.getHeaderWithToken()})
    }

    }