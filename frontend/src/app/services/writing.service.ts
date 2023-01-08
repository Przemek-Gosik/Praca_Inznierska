import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenService } from "./token.service";
import { Lesson, Module, WritingLessonResult, WritingText, WritingTextResult } from "../models/writing-model";

@Injectable({
    providedIn: 'root'
  })

  export class WritingService{
    apiUrl = 'http://localhost:8080/api/fast_writing';

    constructor(private http: HttpClient,private tokenService : TokenService) {
    }

    getAllModules():Observable<Module[]>{
        return this.http.get<Module[]>(`${this.apiUrl}/guest`);
    }

    getAllUserModules():Observable<Module[]>{
      return this.http.get<Module[]>(`${this.apiUrl}`,{headers:this.tokenService.getHeaderWithToken()});
    }

    getLessonById(lessonId: number) : Observable<Lesson>{
      return this.http.get<Lesson>(`${this.apiUrl}/guest/lesson/${lessonId}`);
    }

    getLessonResultById(resultId: number) : Observable<WritingLessonResult>{
      return this.http.get<WritingLessonResult>(`${this.apiUrl}/lesson/result/${resultId}`,{headers: this.tokenService.getHeaderWithToken()});
    }

    updateLessonResult(result: WritingLessonResult){
      return this.http.put(`${this.apiUrl}/lesson/result`,result,{headers: this.tokenService.getHeaderWithToken()});
    }

    saveLessonResult(result: WritingLessonResult){
      return this.http.post(`${this.apiUrl}/lesson/result`,result,{headers: this.tokenService.getHeaderWithToken()});
    }

    getAllTexts():Observable<WritingText[]>{
      return this.http.get<WritingText[]>(`${this.apiUrl}/guest/text`);
    }

    getAllTextsByLevel(level: string):Observable<WritingText[]>{
      return this.http.get<WritingText[]>(`${this.apiUrl}/guest/text/level/${level}`);
    }

    getTextById(textId: number):Observable<WritingText>{
      return this.http.get<WritingText>(`${this.apiUrl}/guest/text/${textId}`);
    }

    drawText():Observable<WritingText>{
      return this.http.get<WritingText>(`${this.apiUrl}/guest/text/draw`);
    }

    drawTextByLevel(level: String):Observable<WritingText>{
      return this.http.get<WritingText>(`${this.apiUrl}/guest/text/draw/${level}`);
    }

    saveTextResult(result:WritingTextResult){
      return this.http.post(`${this.apiUrl}/text/result`,result,{headers: this.tokenService.getHeaderWithToken()});
    }

  }