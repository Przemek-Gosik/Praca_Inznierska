import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { ButtonNames } from 'src/app/consts/button-names-consts';
import { WritingTextResult, WritingText } from 'src/app/models/writing-model';
import { GameService } from 'src/app/services/game.service';
import { TimerService } from 'src/app/services/timer.service';
import { WritingService } from 'src/app/services/writing.service';
import { WritingTextResultDialogComponent } from './writing-text-result-dialog/writing-text-result-dialog.component';

@Component({
  selector: 'app-writing-test',
  templateUrl: './writing-test.component.html',
  styleUrls: ['./writing-test.component.css']
})
export class WritingTestComponent implements OnInit,GameService {

  id: number = 0
  isDrawed: boolean = false
  done: boolean = false
  timeElapsed: number = 0
  dateTime: string = ""
  blockTyping: boolean = true
  writingText: WritingText = {
    idWritingText: 0,
    text: "",
    title: "",
    level: ""
  }
  
  buttonActionName :string = ButtonNames.START_NAME;
  text :string[] = []
  typedWords: string[] = []

  constructor(private route: ActivatedRoute,
    private writingService: WritingService,
    public timerService: TimerService,
    private router: Router,
    private dialog: MatDialog) { }

  startOrPause():void {
    if(this.buttonActionName == ButtonNames.START_NAME){
      this.buttonActionName = ButtonNames.PAUSE_NAME
      this.timerService.startTimer()
      this.blockTyping = false
      this.done = false
    }else{
      this.done = true
      this.buttonActionName = ButtonNames.START_NAME
      this.timerService.stopTimer()
      this.blockTyping = true
    }
  }

  reset(): void { 
    this.timerService.clearTimer()
    this.buttonActionName = ButtonNames.START_NAME
    this.typedWords = []
    this.done = false
    this.timeElapsed = 0
  }

  calculatePoints(): void {
    var points : number = 0
    let texts : string[] = this.writingText.text.split(" ")

    for(let i=0;i< this.typedWords.length;i++){
      console.log(texts[i])
      console.log(this.typedWords[i])

      for(let j = 0;j<this.typedWords[i].length;j++){
        if(this.typedWords[i].charAt(j) === texts[i].charAt(j)){
          points += 1
        }
      }
      console.log(points)
    }
    var result: WritingTextResult = {
      idText: this.writingText.idWritingText,
      typedText: this.typedWords.join(""),
      score: points,
      startTime: this.dateTime,
      time: this.timerService.calculateTime()
    }
    this.openDialog(result)
  }

  openDialog(result: WritingTextResult):void{
    this.dialog.open(WritingTextResultDialogComponent,{
      width: '500px',
      height: '500px',
      data:{
        result: result
      }
    })
  }

  goBack(): void {
    this.timerService.stopTimer()
    this.router.navigate(["/writing/text"])
  }

  ngOnInit(): void {
    this.dateTime = this.timerService.getCurrentDate()
    this.route.paramMap.subscribe(param=>{
      let idPom = param.get('id')
      let isDrawedPom = param.get('isDrawed')
      if(idPom && isDrawedPom){
        if(isDrawedPom == "true"){
          let levelPom = param.get('level')
          if(levelPom){
            this.writingService.drawTextByLevel(levelPom).subscribe((res:WritingText)=>{
              this.writingText = res
              this.text=this.writingText.text.split(" ")
            })
          }else{
            this.writingService.drawText().subscribe((res:WritingText)=>{
              this.writingText = res
              this.text=this.writingText.text.split(" ")
            })
          }
        }else{
          this.writingService.getTextById(parseInt(idPom)).subscribe((res:WritingText)=>{
            this.writingText = res
            this.text=this.writingText.text.split(" ")
            console.log(res)
          })
        }

      }
    })
    this.timerService.clearTimer()
  }

  

  calculateInputWidth(word :string){
    return 9*word.length
  }

  setTextColor(i:number,j:number):string{
    if(this.typedWords.length < i+1){
      return "black"
    }else{
    if(j+1>this.typedWords[i].length){
        return "black"
    }else{
      var char : string = this.typedWords[i].charAt(j)
      if(char == this.text[i].charAt(j)){
        return "green"
      }else{
        return "red"
      }
    }
  }
  }


  moveOn(i: number, word: string){
    if(word.length <= this.typedWords[i-1].length){
      let idStr : string = i.toString()
      document.getElementById(idStr)?.focus()
    }
  }

  getMaxLength(word :string):number{
    return word.length
  }

  checkIfNewLine(word: string):boolean{
    if(word.charAt(0).toUpperCase() === word.charAt(0)){
      return true
    }else{
      return false
    }
  }
}
