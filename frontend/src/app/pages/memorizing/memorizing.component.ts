import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TypeMemory } from 'src/app/consts/type-consts';
import { ThemeService } from 'src/app/services/theme.service';
import { MemorzingInstructionComponent } from './memorzing-instruction/memorzing-instruction.component';

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
  constructor(private router: Router, protected themeService: ThemeService,private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  

  openDialog(type:string):void{
    this.dialog.open(MemorzingInstructionComponent,{
      width: '800px',
      data:{
        type: type
      }
    })
  }

}
