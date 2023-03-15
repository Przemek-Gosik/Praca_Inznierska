import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Report } from "../models/report";
import { LocalstorageService } from "./localstorage.service";
import { TokenService } from "./token.service";

@Injectable({
    providedIn: 'root'
  })
  export class ReportService {
  
    private apiUrl="http://localhost:8080/api/report";

    constructor(
        private http: HttpClient,    
        private localstorage: LocalstorageService,
        private tokenService : TokenService) { }

    
    createUserReport(report:Report){
        return this.http.post(`${this.apiUrl}`,report,{headers: this.tokenService.getHeaderWithToken()})
    }

    createReport(report:Report){
        return this.http.post(`${this.apiUrl}/guest`,report)
    }

    getAllReports():Observable<Report[]>{
        return this.http.get<Report[]>(`${this.apiUrl}`,{headers: this.tokenService.getHeaderWithToken()})
    }

    getAllUserReports(username:string):Observable<Report[]>{
        return this.http.get<Report[]>(`${this.apiUrl}/username/${username}`,{headers: this.tokenService.getHeaderWithToken()})
    }

    getReport(id: number):Observable<Report>{
        return this.http.get<Report>(`${this.apiUrl}/${id}`,{headers:this.tokenService.getHeaderWithToken()})
    }

  }