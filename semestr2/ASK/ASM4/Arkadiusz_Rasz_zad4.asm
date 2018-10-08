.data
	buffer: .space 64

.text
main:	li $v0 5
	syscall
	move $t0 $v0					#number of lines in $t0					#counter in $t1
	li $t1 0						#word counter in $t1
	
	loop:	beqz $t0 display
		sub $t0 $t0 1
		li $v0 8
		la $a0 buffer
		li $a1 63
		syscall
		
		la $t2 buffer				#adress in $t2
		nextword:
		subi $sp $sp 1
		li $t3 '\0'				#add null terminator 
		sb $t3 ($sp)
		addi $t1 $t1 1
		loop2:	lb $t3 ($t2)			#char in $t3
			addi $t2 $t2 1
			beq $t3 '\n' loop
			beq $t3 '\0' loop
			beq $t3 ' ' nextword
			subi $sp $sp 1
			sb $t3 ($sp)
			j loop2
		
	display:	beqz $t1 end
		subi $t1 $t1 1
		move $a0 $sp
		jal reverse				#reverse string on top of the stack
		li $v0 4					#print from stack(until null terminator)
		syscall
		
		loop3:	lb $t2 ($sp)			#move stack to next word
			beq $t2 '\0' next
			addi $sp $sp 1
			j loop3
		next:
		li $a0 ' '
		li $v0 11
		syscall
		
		addi $sp $sp 1
		j display
		
	reverse:	move $t2 $a0				#create two pointers, one for start of string
		move $t3 $a0				#second for end of string
		loop4:	lb $t4 ($t3)
			beq $t4 '\0' next2		#move pointer $t3 to end of string
			addi $t3 $t3 1
			j loop4	
		next2:
		subi $t3 $t3 1				#move back 1
		loop5:	bgt $t2 $t3 endreverse
			lb $t4 ($t2)			#swap chars
			lb $t5 ($t3)
			sb $t4 ($t3)
			sb $t5 ($t2)
			addi $t2 $t2 1			#go towards middle
			subi $t3 $t3 1
			j loop5
		endreverse:
		jr $ra
	
	end:	li $v0 10				#terminate program
		syscall