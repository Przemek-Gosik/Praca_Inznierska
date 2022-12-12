import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenService } from "./token.service";
import { Module } from "../models/writing-model";

@Injectable({
    providedIn: 'root'
  })

  export class WritingService{
    apiUrl = 'http://localhost:8080/api/fast_writing/';

    constructor(private http: HttpClient,private tokenService : TokenService) {
    }

    getAllModules():Observable<Module[]>{
        return this.http.get<Module[]>(`${this.apiUrl}guest`);
    }
  }