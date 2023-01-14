import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TypeMemory } from 'src/app/consts/type-consts';

@Component({
  selector: 'app-memorizing',
  templateUrl: './memorizing.component.html',
  styleUrls: ['./memorizing.component.css']
})
export class MemorizingComponent implements OnInit {
  title: string = "Szybkie zapamiętywanie";
  memoryGame: string = "Dopasuj obrazki";
  mnemonicsGame: string = "Cyferki";
  memory: string = TypeMemory.MEMORY;
  mnemonics: string = TypeMemory.MNEMONICS;
  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  linkToLevelChoice(type:string):void{
    this.router.navigate(['/courses/memorizing/level',
    {
      type: type
    }])
  }

}
