import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemorizngMnemonicsComponent } from './memorizng-mnemonics.component';

describe('MemorizngMnemonicsComponent', () => {
  let component: MemorizngMnemonicsComponent;
  let fixture: ComponentFixture<MemorizngMnemonicsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemorizngMnemonicsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemorizngMnemonicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
