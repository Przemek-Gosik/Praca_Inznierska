import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { MemorizingNumbers, MemorizngResult } from "../models/memorizing-model";
import { TokenService } from "./token.service";

@Injectable({
    providedIn: 'root'
  })

export class MemorizingService{
    apiUrl = 'http://localhost:8080/api/memorizing';

  constructor(private http: HttpClient,private tokenService : TokenService) {
    }   

    generateRandomNumbers(level:string):Observable<MemorizingNumbers>{
        return this.http.get<MemorizingNumbers>(`${this.apiUrl}/guest/numbers/${level}`);
    }

    saveResult(result:MemorizngResult){
      return this.http.post(`${this.apiUrl}`,result,{headers:this.tokenService.getHeaderWithToken()});
    }

    getAllUserResults():Observable<MemorizngResult[]>{
      return this.http.get<MemorizngResult[]>(`${this.apiUrl}`,{headers:this.tokenService.getHeaderWithToken()});
    }

    getResultDetailsById(id:number):Observable<MemorizngResult>{
      return this.http.get<MemorizngResult>(`${this.apiUrl}/${id}`,{headers:this.tokenService.getHeaderWithToken()});
    }

    getResultsByType(type:string):Observable<MemorizngResult[]>{
      return this.http.get<MemorizngResult[]>(`${this.apiUrl}/type/${type}`,{headers:this.tokenService.getHeaderWithToken()});
    }

}