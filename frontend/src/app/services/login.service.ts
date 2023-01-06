import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import { UserLogin } from '../models/userLogin';
import { AuthenticationService } from './authentication.service';
import { LocalstorageService } from './localstorage.service';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private apiUrl="http://localhost:8080/api/auth/";

  constructor(
    private http: HttpClient,    
    private localstorage: LocalstorageService,
    private tokenService : TokenService,
    private authenticationService: AuthenticationService) { }

  loginUser(user: UserLogin):Observable<Object>{
    return this.http.post(`${this.apiUrl}login`,user);
  }
  
  public logoutUser(){
    this.localstorage.removeItemFromStorage('token')
    this.localstorage.removeItemFromStorage('user')
    this.localstorage.removeItemFromStorage('setting')
    this.authenticationService.logOutUser()
  }

  public loggedInUser(){
    return !!this.localstorage.getItemFromStorage('token')
  }

  deleteUser():Observable<any>{
    return this.http.delete(`${this.apiUrl}deleteUser`, {headers: this.tokenService.getHeaderWithToken() });
  }

}
