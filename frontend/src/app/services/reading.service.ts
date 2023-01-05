import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenService } from "./token.service";

@Injectable({
    providedIn: 'root'
  })

export class ReadingService{
    apiUrl = 'http://localhost:8080/api/fast_reading/';

    constructor(private http: HttpClient,private tokenService : TokenService) {
    }

    getNumbersForSchubertTable(level:string):Observable<number[]>{
        return this.http.get<number[]>(`${this.apiUrl}text/guest/numbers/${level}`);
    }

}