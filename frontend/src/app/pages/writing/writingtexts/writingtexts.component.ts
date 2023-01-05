import { Component, OnInit } from '@angular/core';
import { LevelSelect } from 'src/app/models/level-model';
import { LevelConsts } from 'src/app/consts/level-consts';
import { WritingText } from 'src/app/models/writing-model';
import { WritingService } from 'src/app/services/writing.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-writingtexts',
  templateUrl: './writingtexts.component.html',
  styleUrls: ['./writingtexts.component.css']
})
export class WritingtextsComponent implements OnInit {

  levels : LevelSelect[] = LevelConsts.LEVEL_SELECT 
  chosenLevelName: string = ""
  chooseText: boolean = false
  writingTexts : WritingText[] = []
  constructor(private httpWriting: WritingService,private router: Router) { }

  ngOnInit(): void {
  }

  showTexts(){
    this.chooseText = true
    if(this.chosenLevelName){
      this.httpWriting.getAllTextsByLevel(this.chosenLevelName).subscribe((res:WritingText[])=>{
        this.writingTexts = res
      })
    }else{
      this.httpWriting.getAllTexts().subscribe((res:WritingText[])=>{
        this.writingTexts = res
      })
    }
  }

  getLevelName(i : number):string{
    var levelName :string = this.writingTexts[i].level
    switch(levelName){
      case "EASY":
        return "Łatwy"
        break;
      case "MEDIUM":
        return "Średni";
        break;
        case "ADVANCED":
          return "Trudny";
          break
        default:
          return "Nieznany"
          break

    }
   
  }

  startDrawedTest(){
    this.router.navigate(["/writing/text/test", {id :
      0,
      isDrawed: true }
    ])
  }

  startTest(i: number){
    var textId : number = this.writingTexts[i].idFastWritingText!
    var isDrawed: boolean = false
    this.router.navigate(["/writing/text/test", {id :
      textId,
      isDrawed: isDrawed }
    ])
  }

}
