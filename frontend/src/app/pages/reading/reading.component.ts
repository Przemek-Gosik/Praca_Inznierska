import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TypeReading } from 'src/app/consts/type-consts';

@Component({
  selector: 'app-reading',
  templateUrl: './reading.component.html',
  styleUrls: ['./reading.component.css']
})
export class ReadingComponent implements OnInit {
  title: string = "Szybkie czytanie";
  SCHULTZ: string = TypeReading.SCHULTZ
  constructor(private router:Router) { }

  ngOnInit(): void {
  }

  tabSchultz: string = "Tablice Schulza"

  linkToLevelChoice(type:string){
    this.router.navigate(["/reading/level",
    {
      type: type
    }])
  }

}
