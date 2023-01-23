import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemorzingInstructionComponent } from './memorzing-instruction.component';

describe('MemorzingInstructionComponent', () => {
  let component: MemorzingInstructionComponent;
  let fixture: ComponentFixture<MemorzingInstructionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemorzingInstructionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemorzingInstructionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
