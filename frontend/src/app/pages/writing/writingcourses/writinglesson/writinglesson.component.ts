import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Lesson, Result } from 'src/app/models/writing-model';
import { WritingService } from 'src/app/services/writing.service';
import { SplitPipe } from '../../pipe/splitpipe';
import { WritingResultDialogComponent } from './writing-result-dialog/writing-result-dialog.component';
@Component({
  selector: 'app-writinglesson',
  templateUrl: './writinglesson.component.html',
  styleUrls: ['./writinglesson.component.css']
})
export class WritinglessonComponent implements OnInit {
  id : number = 0
  typedTexts : string[] = []
  lesson : Lesson = {
    idFastWritingLesson: 0,
    idFastWritingCourse: 0,
    name: "",
    score: 0,
    number: 0,
    generatedCharacters: "",
    text: []
  }
  result : Result = {
    idFastWritingCourse: 0,
    startTime: 0,
    score: 0,
    typedLetters: 0
  }
  startName : string = "Start"
  pauzeName: string ="Pauza"
  buttonActionName : string = this.startName
  done: boolean = false

  constructor(private router:Router,private route: ActivatedRoute,private writingService: WritingService,public dialog: MatDialog) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(param=>{
      let idPom = param.get('id')
      if(idPom){
        this.id=parseInt(idPom)
        this.getLessonById(this.id)
      }
    })
  }

  getLessonById(id: number) {
    this.writingService.getLessonById(id).subscribe((res)=>{
      this.lesson = res
      console.log(this.lesson.name)
    })
  }

  checkText(typedText: string,text:string,i:number){
    let length = typedText.length
    this.typedTexts[i] = text
        if(typedText.charAt(length-1) == text.charAt(length-1)){
          
      }
  }
  moveOnNext(i : number,text:string){
    let nextInputId = i+1
    let str : string = nextInputId.toString()
    this.typedTexts[i] = text
    document.getElementById(str)?.focus()
  }

  startOrPauze(){
    if(this.buttonActionName == this.startName){
    this.buttonActionName = this.pauzeName
    }else{
      this.buttonActionName = this.startName
    }
  }

  openDialog(score: number, typedLetters : number
  ):void {
    console.log(score)
    this.dialog.open(WritingResultDialogComponent, {
      width: '1000px',
      height: '1000px'
  }).afterClosed().subscribe(() =>{
    this.router.navigate(["/writing/course"])
  })
  }

  calculatePoints(){
    let points : number = 0
    let typedLettes : number = 0
    let texts : string[] = this.lesson.text
    for(let i = 0 ;i<texts.length ; i++){
      let textPom :string = this.typedTexts[i]
        for( let j = 0 ; j<textPom.length ;j++){
              if(textPom.charAt(j) === texts[i].charAt(j)){
                points +=1
                typedLettes +=1
              }
        }
        console.log(points)
    }
    this.lesson.score=points
    //this.openDialog(points,typedLettes)
    this.done = true
  }

}
