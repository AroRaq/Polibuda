.data
	key: .space 64
	msg: .space 64
	ret: .space 64

.text
	# load operation
	li $v0 5
	syscall
	move $t0 $v0
	# read key
	li $v0 8
	la $a0 key
	li $a1 64
	syscall
	# read msg
	li $v0 8
	la $a0 msg
	li $a1 64
	syscall
	# load strings to registers
	la $t7 key
	la $t8 msg
	la $t9 ret
	
	beqz $t0 encrypt
	
	decrypt:
		lb $t1 0($t7)
		lb $t2 0($t8)
		li $t5 '\n'
		beq $t2 $t5 end			# 10 - new line
		bne $t1 $t5 next1		# if not end of key - skip resetting key
			la $t7 key
			lb $t1 0($t7)
		next1:
		addi $t2 $t2 65
		sub $t2 $t2 $t1
		bgt $t2 64 next2
			addi $t2 $t2 26
		next2:
		sb $t2 ($t9)
		
		addi $t7 $t7 1			# increment the address
		addi $t8 $t8 1
		addi $t9 $t9 1
		j decrypt
	
	encrypt:
		lb $t1 0($t7)
		lb $t2 0($t8)
		li $t5 '\n'
		beq $t2 $t5 end			# 10 - new line
		bne $t1 $t5 next3		# if not end of key - skip resetting key
			la $t7 key
			lb $t1 0($t7)
		next3:
		subi $t2 $t2 65
		add $t2 $t1 $t2
		blt $t2 91 next4
			subi $t2 $t2 26		# loop back when greater than 'Z'
		next4:
		sb $t2 ($t9)
		
		addi $t7 $t7 1			# increment the address
		addi $t8 $t8 1
		addi $t9 $t9 1
		j encrypt
	end:
	la $a0 ret
	li $v0 4
	syscall
	
	li $v0 10
	syscall					#terminate
	
