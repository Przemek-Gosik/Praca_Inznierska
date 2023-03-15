export class Circle{
   private x = 0;
    

    constructor(private renderingContext: CanvasRenderingContext2D){}

    draw(){
        this.renderingContext.fillStyle = this.color
        
    }
}