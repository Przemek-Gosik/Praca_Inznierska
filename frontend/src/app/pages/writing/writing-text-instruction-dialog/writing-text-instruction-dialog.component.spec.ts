import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingTextInstructionDialogComponent } from './writing-text-instruction-dialog.component';

describe('WritingTextInstructionDialogComponent', () => {
  let component: WritingTextInstructionDialogComponent;
  let fixture: ComponentFixture<WritingTextInstructionDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritingTextInstructionDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritingTextInstructionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
