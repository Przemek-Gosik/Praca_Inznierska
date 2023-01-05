import { Component, OnInit } from '@angular/core';
import { Table } from 'src/app/models/reading-model';
import { ReadingService } from 'src/app/services/reading.service';

@Component({
  selector: 'app-schubert',
  templateUrl: './schubert.component.html',
  styleUrls: ['./schubert.component.css']
})
export class SchubertComponent implements OnInit {
  numbers: number[] = []
  tableRows : Table[] = []
  currentNumber : number = 1
  points: number = 0
  size: number = 0
  constructor(private httpReading: ReadingService) { }

  ngOnInit(): void {
    this.httpReading.getNumbersForSchubertTable("EASY").subscribe((res:number[])=>{
      this.numbers = res
      this.size = this.numbers.length
      this.createTable()
    })
  }

  createTable(){
    var columnSize : number = Math.sqrt(this.size)
    for(var i=0;i<this.size;i+=columnSize){
      var pom: number[] = this.numbers.slice(i,columnSize+i)
      var columnPom : Table = {
      column: pom
    }
    this.tableRows.push(columnPom)
    }
  }

  checkPoints(num :number){
    if(num==this.currentNumber){
      this.points += 1
      this.currentNumber += 1
    }else{
      this.points -= 1
    }
  }

  checkColumn(i:number):boolean{
    var numberOfColumns = Math.sqrt(this.size)
    if(numberOfColumns % i ==0 ){
      return true
    }else{
      return false
    }
  }

}
