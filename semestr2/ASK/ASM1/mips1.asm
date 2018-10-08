.data
again: .asciiz "\nagain? "
error: .asciiz "Dzielenie przez zero"
intro: .asciiz "Dzialania: 0-add, 1-sub, 2-mul, 3-div\n"


.text
main:
	li $v0 4			#display message asking if again
	la $a0 intro
	syscall
	#read operation
	li $v0 5
	syscall
	move $t1 $v0
	bge $t1 4 main			#check if correct input
	blt $t1 0 main
	#read a
	li $v0 5
	syscall
	move $t0 $v0
	#read b
	li $v0 5
	syscall
	move $t2 $v0
	#jump to operation
	beq $t1 0 OPadd
	beq $t1 1 OPsub
	beq $t1 2 OPmult
	j OPdiv
	
	OPadd:
		add $t3 $t0 $t2
		j display
	OPsub:
		sub $t3 $t0 $t2
		j display
	OPmult:
		mul $t3 $t0 $t2
		j display
		#mult $t0 $t2
		#mflo $t3
	OPdiv:
		beqz $t2 divzero	#check if dividing by 0
		div $t3 $t0 $t2
		j display
	divzero:
		li $v0 4
		la $a0 error
		syscall
		j loop
	display:
	li $v0 1			#display result
	move $a0 $t3
	syscall
	
	loop:
	li $v0 4			#display message asking if again
	la $a0 again
	syscall
	
	li $v0 5			#load int
	syscall
	beqz $v0 exit
		j main
	exit:
	li $v0 10			#terminate program
	syscall
