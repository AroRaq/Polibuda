.data

.text
main:
	#load int
	li $0 5
	syscall
	move $t0 $v0			#counter in $t0
	
	li $v0 9
	li $a0 32			#allocate memory in heap
	syscall
	move $t1 $v0
	
	loop:
		beqz $t0 end		#decrement counter
		subi $t0 1
		
		
		
		li $v0 8		#read string
		la $a0 ($t0)
		li $a1 31
		syscall
		
		li $t2 0
		
		loop2:
		beq
		
		saveword:
		
	end:
