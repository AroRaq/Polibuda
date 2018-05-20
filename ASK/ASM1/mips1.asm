.data
again: .asciiz "\nagain?"


.text
main:
	li $v0 5
	syscall
	move $t0 $v0
	li $v0 5
	syscall
	move $t1 $v0
	li $v0 5
	syscall
	move $t2 $v0
	beq $t1 0 OPadd
	beq $t1 1 OPsub
	beq $t1 2 OPmult
	j OPdiv
	
	OPadd:
		add $t3 $t0 $t2
		j skip
	OPsub:
		sub $t3 $t0 $t2
		j skip
	OPmult:
		mul $t3 $t0 $t2
		j skip
	OPdiv:
		div $t3 $t0 $t2
	
	skip:
	li $v0 1
	move $a0 $t3
	syscall
	li $v0 4
	la $a0 again
	syscall
	
	li $v0 5
	syscall
	beqz $v0 exit
		j main
	exit:
	li $v0 10
	syscall