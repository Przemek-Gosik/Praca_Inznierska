import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalstorageService } from './localstorage.service';
@Injectable({
    providedIn: 'root'
  })

export class TokenService{

    constructor(private localstorageSerivce:LocalstorageService){

    }
    
    public getHeaderWithToken() : HttpHeaders{
        let token : string | undefined = this.localstorageSerivce.getItemFromStorage("token");
        return new HttpHeaders().set('Authorization',`Bearer${token}`);
    }
}