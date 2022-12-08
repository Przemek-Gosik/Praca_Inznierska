import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemorizingComponent } from './memorizing.component';

describe('MemorizingComponent', () => {
  let component: MemorizingComponent;
  let fixture: ComponentFixture<MemorizingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemorizingComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MemorizingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
