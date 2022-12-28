import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenService } from "./token.service";
import { Lesson, Module, WritingResult, WritingText } from "../models/writing-model";

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

    getLessonById(lessonId: number) : Observable<Lesson>{
      return this.http.get<Lesson>(`${this.apiUrl}guest/lesson/${lessonId}`);
    }

    getCourseById(courseId: number) : Observable<WritingResult>{
      return this.http.get<WritingResult>(`${this.apiUrl}course/${courseId}`,{headers: this.tokenService.getHeaderWithToken()});
    }

    updateCourse(course: WritingResult){
      return this.http.put(`${this.apiUrl}course`,course,{headers: this.tokenService.getHeaderWithToken()});
    }

    saveCourse(course: WritingResult){
      return this.http.post(`${this.apiUrl}course`,course,{headers: this.tokenService.getHeaderWithToken()});
    }

    getAllTexts():Observable<WritingText[]>{
      return this.http.get<WritingText[]>(`${this.apiUrl}guest/text`);
    }

    getAllTextsByLevel(level: string):Observable<WritingText[]>{
      return this.http.get<WritingText[]>(`${this.apiUrl}guest/text/level/${level}`);
    }

    getTextById(textId: number):Observable<WritingText>{
      return this.http.get<WritingText>(`${this.apiUrl}guest/text/${textId}`);
    }

    drawText():Observable<WritingText>{
      return this.http.get<WritingText>(`${this.apiUrl}guest/draw`);
    }

    drawTextByLevel(level: String):Observable<WritingText>{
      return this.http.get<WritingText>(`${this.apiUrl}guest/draw/${level}`);
    }

  }