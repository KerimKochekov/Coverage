# COMP 5111 - Assignment 1

## Structure
- Test suites of Task 1 are under `${PROJECT_ROOT}/src/test/randoop[0-4]`
- My tool for Task 2-3 is `${PROJECT_ROOT}/src/main/java/comp5111/assignment/Assignment1.java`
- Auxiliary files for Task 2-3 are under `${PROJECT_ROOT}/src/main/java/comp5111/assignment/cut`
- Screenshots of 5 test suites under `${PROJECT_ROOT}/screenshots`:
  - File name for branch coverages: `./screenshots/randoop[0-4]_branch`
  - File name for statement coverages: `./screenshots/randoop[0-4]_statement`
- Script for generating tests is `${PROJECT_ROOT}/gen-tests.sh`
- Script for running `Assignment1.java` is `${PROJECT_ROOT}/run_soot.sh`

## How to run?
```
$ cd scripts
$ chmod +x run_soot.sh
$ ./run_soot.sh
```

Edit `run_soot.sh` based on your need (to specify test suite and specific classname). Follow the guidline `Assignment1.java` for more information.

## Experiments

`test/randoop0`
<table>

<tr>
<th>Statement coverage</th>
<th>Branch coverage</th>
<th>Line coverage</th>
</tr>

<tr>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 93.0% | 91.3% |
|Array | 86.1% | 83.3% |
|Gregorian | 34.2% | 35.6% |
|Filename | 49.2% | 51.5% |
|String | 62.8% | 64.1% |
|Number | 38.4% | 37.7% |
</td>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 70.8% | 70.8% |
|Array | 77.3% | 77.3% |
|Gregorian | 34.0% | 35.1% |
|Filename | 33.7% | 33.7% |
|String | 50.0% | 50.0% |
|Number | 23.3% | 26.2% |
</td>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 100.0% | 100.0% |
|Array | 85.0% | 84.2% |
|Gregorian | 25.0% | 25.8% |
|Filename | 49.0% | 51.0% |
|String | 68.2% | 68.6% |
|Number | 34.3% | 42.9% |
</td></tr> 
</table>

`test/randoop1`
<table>

<tr>
<th>Statement coverage</th>
<th>Branch coverage</th>
<th>Line coverage</th>
</tr>

<tr>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 85.7% | 77.8% |
|Char | 93.0% | 91.3% |
|Array | 64.6% | 64.6% |
|Gregorian | 33.9% | 32.9% |
|Filename | 49.2% | 51.5% |
|String | 68.8% | 68.1% |
|Number | 38.4% | 37.7% |
</td>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 75.0% | 75.0% |
|Char | 70.8% | 70.8% |
|Array | 45.5% | 45.5% |
|Gregorian | 30.9% | 31.9% |
|Filename | 33.7% | 33.7% |
|String | 55.3% | 55.3% |
|Number | 23.3% | 26.2% |
</td>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 100.0 % | 100.0% |
|Array | 70.0% | 68.4% |
|Gregorian | 25.0% | 24.2% |
|Filename | 49.0% | 51.0% |
|String | 73.2% | 73.2% |
|Number | 34.3% | 42.9% |
</td></tr> 
</table>

`test/randoop2`
<table>

<tr>
<th>Statement coverage</th>
<th>Branch coverage</th>
<th>Line coverage</th>
</tr>

<tr>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 93.0% | 91.3% |
|Array | 91.1% | 89.6% |
|Gregorian | 48.6% | 47.9% |
|Filename | 49.2% | 51.5% |
|String | 62.8% | 64.4% |
|Number | 38.4% | 37.7% |
</td>
<td>


| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 81.8% | 70.8% |
|Array | 70.8% | 81.8% |
|Gregorian | 40.4% | 40.4% |
|Filename | 33.7% | 33.7% |
|String | 49.3% | 49.3% |
|Number | 23.3% | 26.2% |
</td>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 100.0% | 100.0% |
|Array | 90.0% | 89.5% |
|Gregorian | 47.1% | 48.4% |
|Filename | 49.0% | 51.0% |
|String | 67.5% | 68.0% |
|Number | 34.3% | 42.9% |
</td></tr> 
</table>

`test/randoop3`
<table>

<tr>
<th>Statement coverage</th>
<th>Branch coverage</th>
<th>Line coverage</th>
</tr>

<tr>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 93.0% | 91.3% |
|Array | 94.9% | 95.8% |
|Gregorian | 34.2% | 35.6% |
|Filename | 48.7% | 51.0% |
|String | 65.0% | 66.1% |
|Number | 38.4% | 37.7% |
</td>
<td>
  
| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 70.8% | 70.8% |
|Array | 86.4% | 86.4% |
|Gregorian | 33.0% | 34.0% |
|Filename | 32.7% | 32.7% |
|String | 53.3% | 53.3% |
|Number | 23.3% | 26.2% |
</td>
<td>
  
| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 100.0% | 100.0% |
|Array | 90.0% | 89.5% |
|Gregorian | 25.0% | 25.8% |
|Filename | 48.0% | 50.0% |
|String | 70.1% | 70.6% |
|Number | 34.3% | 42.9% |
</td></tr> 
</table>

`test/randoop4`
<table>

<tr>
<th>Statement coverage</th>
<th>Branch coverage</th>
<th>Line coverage</th>
</tr>

<tr>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 93.0% | 91.3% |
|Array | 72.2% | 70.8% |
|Gregorian | 32.5% | 32.2% |
|Filename | 49.2% | 51.5% |
|String | 69.6% | 67.8% |
|Number | 37.0% | 36.9% |
</td>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char |70.8% | 70.8% |
|Array | 54.5% | 54.5% |
|Gregorian | 33.0% | 34.0% |
|Filename | 33.7% | 33.7% |
|String | 53.3% | 53.3% |
|Number | 22.2% | 25.6% |
</td>
<td>

| Class | EclEmma | My tool |
| :---:   | :---: | :---: |
|Boolean | 100.0% | 100.0% |
|Char | 100.0% | 100.0% |
|Array | 75.0% | 73.7% |
|Gregorian | 25.0% | 25.8% |
|Filename | 49.0% | 51.0% |
|String | 72.0% | 71.2% |
|Number | 32.5% | 42.3% |
</td></tr> 
</table>
