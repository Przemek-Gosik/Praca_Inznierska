import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { FidningNumbers, ReadingResult } from "../models/reading-model";
import { TokenService } from "./token.service";

@Injectable({
    providedIn: 'root'
  })

export class ReadingService{
    apiUrl = 'http://localhost:8080/api/fast_reading';

    constructor(private http: HttpClient,private tokenService : TokenService) {
    }

    getNumbersForSchubertTable(level:string):Observable<number[]>{
        return this.http.get<number[]>(`${this.apiUrl}/text/guest/numbers/${level}`);
    }

    getNumbersForFindingGame(level:string):Observable<FidningNumbers>{
        return this.http.get<FidningNumbers>(`${this.apiUrl}/text/guest/finding_numbers/${level}`);
    }

    saveResult(result:ReadingResult){
        return this.http.post(`${this.apiUrl}`,result,{headers:this.tokenService.getHeaderWithToken()});
    }

    getAllResults():Observable<ReadingResult[]>{
        return this.http.get<ReadingResult[]>(`${this.apiUrl}`,{headers:this.tokenService.getHeaderWithToken()});
    }

    getAllResultsByType(type: string):Observable<ReadingResult[]>{
        return this.http.get<ReadingResult[]>(`${this.apiUrl}/${type}`,{headers:this.tokenService.getHeaderWithToken()});
    }

}