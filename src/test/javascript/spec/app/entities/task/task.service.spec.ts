import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DATE_FORMAT } from 'app/core/config/input.constants';
import { DatePipe } from '@angular/common';
import { TaskService } from 'app/entities/task/task.service';
import { ITask, Task } from 'app/shared/model/task.model';
import { TaskType } from 'app/shared/model/enumerations/task-type.model';

describe('Service Tests', () => {
  describe('Task Service', () => {
    let service: TaskService;
    let httpMock: HttpTestingController;
    let elemDefault: ITask;
    let expectedResult: ITask | ITask[] | boolean | null;
    let currentDate: Date;
    let datePipe: DatePipe;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [DatePipe],
      });
      expectedResult = null;
      service = TestBed.inject(TaskService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = new Date();
      datePipe = TestBed.inject(DatePipe);

      elemDefault = new Task(
        0,
        'AAAAAAA',
        TaskType.TYPE1,
        currentDate,
        currentDate,
        currentDate,
        false,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            endDate: datePipe.transform(currentDate, DATE_FORMAT),
            createdAt: currentDate.toISOString(),
            modifiedAt: currentDate.toISOString(),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Task', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            endDate: datePipe.transform(currentDate, DATE_FORMAT),
            createdAt: currentDate.toISOString(),
            modifiedAt: currentDate.toISOString(),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDate: currentDate,
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );

        service.create(new Task()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Task', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            type: 'BBBBBB',
            endDate: datePipe.transform(currentDate, DATE_FORMAT),
            createdAt: currentDate.toISOString(),
            modifiedAt: currentDate.toISOString(),
            done: true,
            description: 'BBBBBB',
            attachment: 'BBBBBB',
            picture: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDate: currentDate,
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Task', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            type: 'BBBBBB',
            endDate: datePipe.transform(currentDate, DATE_FORMAT),
            createdAt: currentDate.toISOString(),
            modifiedAt: currentDate.toISOString(),
            done: true,
            description: 'BBBBBB',
            attachment: 'BBBBBB',
            picture: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDate: currentDate,
            createdAt: currentDate,
            modifiedAt: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Task', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
