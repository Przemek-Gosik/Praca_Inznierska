import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserLogin } from '../models/userLogin';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private apiUrl="http://localhost:8080/api/auth/login";

  constructor(private http: HttpClient) { }

  loginUser(user: UserLogin):Observable<Object>{
    console.log(user);
    return this.http.post(`${this.apiUrl}`,user);
  }
}
