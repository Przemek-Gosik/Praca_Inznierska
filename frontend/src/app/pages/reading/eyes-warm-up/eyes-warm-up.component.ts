import { Component, ElementRef, NgZone, OnInit, ViewChild } from '@angular/core';



@Component({
  selector: 'app-eyes-warm-up',
  templateUrl: './eyes-warm-up.component.html',
  styleUrls: ['./eyes-warm-up.component.css']
})
export class EyesWarmUpComponent implements OnInit {
  @ViewChild('canvas', { static: true })
  canvas!: ElementRef<HTMLCanvasElement>;
  ctx!: CanvasRenderingContext2D;
  interval: number = 0
  requestId: number =0
  

  constructor(private ngZone: NgZone) { }

  ngOnInit(): void {
    this.ctx = this.canvas.nativeElement.getContext('2d')!
    
      
        this.ngZone.runOutsideAngular(()=>
        this.draw())
        setInterval(()=>{
          this.draw()
        },200)
        
       
  }
  
  draw():void{
    for(let i = 0; i < 750; i++){
      this.drawCircle(i,i);
    }
  }

  

  drawCircle(x:number,y:number):void{
      this.ctx.beginPath()
      this.ctx.arc(x,y,20,0,2*Math.PI);
      this.ctx.fill();
      this.interval = requestAnimationFrame(()=>this.drawCircle)
    
   
  }

  clear():void{
    this.ctx.clearRect(0,0,this.ctx.canvas.width,this.ctx.canvas.height)
  }

  start():void{

  }
}
