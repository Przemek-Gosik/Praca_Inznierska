import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { User } from '../models/user';
@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  apiUrl = 'http://localhost:8080/api/auth/register';
  //deklaracja tokena

  // user: User = {email: '', is_email_confirmed: true, login: '', password: ''};

  constructor(private http: HttpClient) {
  }

  registerUser(user: User):Observable<Object>{
    console.log(user);
    return this.http.post(`${this.apiUrl}`,user).
    pipe(tap(console.log));
  }


}
