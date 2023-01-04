import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { TokenService } from "./token.service";
import { Lesson, Module, WritingCourseResult, WritingText } from "../models/writing-model";

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

    getCourseById(courseId: number) : Observable<WritingCourseResult>{
      return this.http.get<WritingCourseResult>(`${this.apiUrl}course/${courseId}`,{headers: this.tokenService.getHeaderWithToken()});
    }

    updateCourse(course: WritingCourseResult){
      return this.http.put(`${this.apiUrl}course`,course,{headers: this.tokenService.getHeaderWithToken()});
    }

    saveCourse(course: WritingCourseResult){
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